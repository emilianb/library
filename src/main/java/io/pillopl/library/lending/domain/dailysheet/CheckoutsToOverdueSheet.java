package io.pillopl.library.lending.domain.dailysheet;

import io.pillopl.library.lending.domain.book.BookId;
import io.pillopl.library.lending.domain.library.LibraryBranchId;
import io.pillopl.library.lending.domain.patron.PatronBooksEvent;
import io.pillopl.library.lending.domain.patron.PatronId;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Stream;
import lombok.Value;

@Value
public class CheckoutsToOverdueSheet {

    List<Tuple3<BookId, PatronId, LibraryBranchId>> checkouts;

    public Stream<PatronBooksEvent.OverdueCheckoutRegistered> toStreamOfEvents() {
        return checkouts
                .toStream()
                .map(this::tupleToEvent);
    }

    public int count() {
        return checkouts.size();
    }

    private PatronBooksEvent.OverdueCheckoutRegistered tupleToEvent(Tuple3<BookId, PatronId, LibraryBranchId> overdueCheckouts) {
        return PatronBooksEvent.OverdueCheckoutRegistered.now(overdueCheckouts._2, overdueCheckouts._1, overdueCheckouts._3);
    }


}
