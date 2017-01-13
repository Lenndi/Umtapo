package org.lendi.umtapo.mapper.converter;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.lendi.umtapo.entity.Library;

/**
 * Convert library to library id.
 */
public class LibraryConverter extends BidirectionalConverter<Integer, Library> {
    /**
     * Convert library id to library.
     *
     * @param libraryId
     * @param destinationType
     * @return
     */
    @Override
    public Library convertTo(Integer libraryId, Type<Library> destinationType) {
        Library library = new Library();
        library.setId(libraryId);

        return library;
    }

    /**
     * Convert library to library id.
     *
     * @param library
     * @param destinationType
     * @return
     */
    @Override
    public Integer convertFrom(Library library, Type<Integer> destinationType) {
        return library.getId();
    }
}
