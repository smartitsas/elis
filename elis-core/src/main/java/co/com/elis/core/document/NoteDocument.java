package co.com.elis.core.document;

import co.com.elis.core.item.Discrepancy;
import co.com.elis.core.item.ItemList;
import co.com.elis.core.item.NoteItem;
import co.com.elis.core.tax.TaxTotal;
import co.com.elis.core.tax.TaxTotalList;
import co.com.elis.core.util.ElisEncoder;
import co.com.elis.exception.ElisCoreException;
import java.math.RoundingMode;
import javax.validation.Valid;
import lombok.Getter;

public abstract class NoteDocument extends Document<NoteItem> {

    @Getter
    private String cude;

    @Valid
    @Getter
    private final Discrepancy discrepancy;

    public NoteDocument(Header header, TaxTotalList taxTotalList, MonetaryTotal monetaryTotal, ItemList<NoteItem> itemList, OtherRelatedData otherData, Discrepancy discrepancy) throws ElisCoreException {
        super(header, taxTotalList, monetaryTotal, itemList, otherData);
        this.discrepancy = discrepancy;
    }

    void calculateCude() throws ElisCoreException {

        StringBuilder builder = new StringBuilder(getHeader().getDocumentNumber().getFullId());

        builder.append(getHeader().getDocumentDate().toFormattedDateTime());
        builder.append(getLegalMonetaryTotal().getLineTotal().setScale(2, RoundingMode.HALF_UP).toPlainString());

        for (TaxTotal taxTotal : getTaxTotalList()) {
            builder.append(taxTotal.getType().getCode());
            builder.append(taxTotal.toPlainString());
        }

        builder.append(getLegalMonetaryTotal().getPayableAmount().setScale(2, RoundingMode.HALF_UP).toPlainString());
        builder.append(getHeader().getSoftware().getNit().toString());
        builder.append(getHeader().getReceiverParty().getIdentityDocument().getAccount());
        builder.append(getHeader().getSoftware().getPin());
        builder.append(getHeader().getSoftware().getEnvironment().toString());

        cude = ElisEncoder.applyHash(builder.toString());
    }

    void setCude(String cude) {
        this.cude = cude;
    }

    @Override
    public String getQR() {
        StringBuilder builder = new StringBuilder(getHeader().getSoftware().getEnvironment().getQrUrl());
        builder.append(this.getCude());
        return builder.toString();
    }

}
