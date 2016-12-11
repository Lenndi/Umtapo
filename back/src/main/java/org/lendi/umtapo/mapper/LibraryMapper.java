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

    private final static MapperFacade mapper;

    static {
        final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(Library.class, LibraryDto.class).exclude("borrowers").byDefault().register();
        mapper = mapperFactory.getMapperFacade();
    }

    public LibraryMapper() {
    }

    public LibraryDto mapLibraryToLibraryDto(Library Library) {
        return mapper.map(Library, LibraryDto.class);
    }

    public Library mapLibraryDtoToLibrary(LibraryDto LibraryDto) {
        return mapper.map(LibraryDto, Library.class);
    }
}