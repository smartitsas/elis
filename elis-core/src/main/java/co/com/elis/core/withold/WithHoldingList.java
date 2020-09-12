package co.com.elis.core.withold;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithHoldingList implements Iterable<WithHold> {

    private final List<WithHold> internalCollection;

    public static WithHoldingList buildWithHoldingList(List<WithHold> withHolds) {
        return new WithHoldingList(withHolds);
    }

    @Override
    public Iterator<WithHold> iterator() {
        return internalCollection.iterator();
    }

    public Optional<WithHold> getByType(WithHoldType withHoldType) {
        return internalCollection.stream()
                .filter(withHold -> withHold.getWithHoldType().compareTo(withHoldType) == 0)
                .findAny();
    }

    public BigDecimal getWithHoldingTotal() {
        return internalCollection.stream()
                .map(withHolding -> withHolding.getWithHoldtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int size() {
        return internalCollection.size();
    }

}
