package org.lendi.umtapo.mapper;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.lendi.umtapo.dto.LibraryDto;
import org.lendi.umtapo.entity.Library;
import org.springframework.stereotype.Component;

/**
 * Library entity to library DTO mapper.
 */
@Component
public class LibraryMapper extends ConfigurableMapper {

    private static final MapperFacade MAPPER;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Library.class, LibraryDto.class).exclude("borrowers").byDefault().register();
        MAPPER = mapperFactory.getMapperFacade();
    }

    /**
     * Instantiates a new Library mapper.
     */
    public LibraryMapper() {
    }

    /**
     * Map library to library dto library dto.
     *
     * @param library the library
     * @return the library dto
     */
    public LibraryDto mapLibraryToLibraryDto(Library library) {
        return MAPPER.map(library, LibraryDto.class);
    }

    /**
     * Map library dto to library library.
     *
     * @param libraryDto the library dto
     * @return the library
     */
    public Library mapLibraryDtoToLibrary(LibraryDto libraryDto) {
        return MAPPER.map(libraryDto, Library.class);
    }
}
