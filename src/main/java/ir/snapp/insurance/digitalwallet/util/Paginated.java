package ir.snapp.insurance.digitalwallet.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.ToString;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO to wrap paginated collection of elements.
 *
 * @author Younes Rahimi
 * @author Mohammad Reza Dehghani
 */
@ToString
@JsonAutoDetect(fieldVisibility = ANY)
public class Paginated<T> {

    /**
     * The current 1-index page number.
     */
    private final int pageNumber;

    /**
     * The size of current page.
     */
    private final int size;

    /**
     * Total number of elements.
     */
    private final Long numberOfElements;

    /**
     * Can this page be followed by another page after it?
     */
    private final boolean hasNext;

    /**
     * Is there a previous page for this page?
     */
    private final boolean hasPrevious;

    /**
     * The actual paginated elements.
     */
    private final List<T> elements;

    public Paginated(int page, int size, Long countAll, List<T> elements) {
        this(
                page,
                size,
                countAll,
                (long) page * size + elements.size() < countAll,
                page > 1,
                getElements(size, elements)
        );
    }

    private Paginated(int pageNumber, int size, Long numberOfElements, boolean hasNext, boolean hasPrevious, List<T> elements) {
        this.pageNumber = pageNumber;
        this.size = size;
        this.numberOfElements = numberOfElements;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.elements = elements;
    }

    private static <T> List<T> getElements(int pageSize, List<T> elements) {
        return elements.size() > pageSize ? elements.subList(0, pageSize) : elements;
    }
}
