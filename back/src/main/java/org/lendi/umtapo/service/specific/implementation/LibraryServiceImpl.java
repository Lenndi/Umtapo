package org.lendi.umtapo.service.specific.implementation;

import org.lendi.umtapo.dao.LibraryDao;
import org.lendi.umtapo.dto.LibraryDto;
import org.lendi.umtapo.entity.Library;
import org.lendi.umtapo.mapper.LibraryMapper;
import org.lendi.umtapo.service.generic.AbstractGenericService;
import org.lendi.umtapo.service.specific.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Library service implementation.
 */
@Service
public class LibraryServiceImpl extends AbstractGenericService<Library, Integer> implements LibraryService {

    private final LibraryDao libraryDao;
    private final LibraryMapper libraryMapper;

    @Autowired
    public LibraryServiceImpl(LibraryMapper libraryMapper, LibraryDao libraryDao) {
        Assert.notNull(libraryMapper, "Argument libraryMapper cannot be null");
        Assert.notNull(libraryDao, "Argument libraryDao cannot be null");

        this.libraryMapper = libraryMapper;
        this.libraryDao = libraryDao;
    }

    @Override
    public Library save(Library library) {
        return this.libraryDao.save(library);
    }

    @Override
    public LibraryDto saveDto(LibraryDto LibraryDto) {
        Library library = this.libraryMapper.mapLibraryDtoToLibrary(LibraryDto);
        library = this.libraryDao.save(library);

        return this.libraryMapper.mapLibraryToLibraryDto(library);
    }

    @Override
    public LibraryDto findOneDto(Integer id) {
        Library library = this.libraryDao.findOne(id);

        return this.libraryMapper.mapLibraryToLibraryDto(library);
    }

    @Override
    public List<LibraryDto> findAllDto() {
        return mapLibrariesToLibrariesDTO(this.libraryDao.findAll());
    }

    /**
     * Map LibraryDto entity to Library entity.
     * @param LibraryDto
     * @return
     */
    private Library mapLibraryDtoToLibrary(LibraryDto LibraryDto) {
        return this.libraryMapper.mapLibraryDtoToLibrary(LibraryDto);
    }

    /**
     * Map Library entity to LibraryDto entity.
     * @param Library
     * @return
     */
    private LibraryDto mapLibraryToLibraryDto(Library Library) {
        return this.libraryMapper.mapLibraryToLibraryDto(Library);
    }

    /**
     * Map a list of LibraryDto entities to a list of Library entities.
     * @param librariesDto
     * @return
     */
    private List<Library> mapLibrariesDtoToLibraries(List<LibraryDto> librariesDto) {
        List<Library> libraries = new ArrayList<>();
        librariesDto.forEach(LibraryDto -> libraries.add(mapLibraryDtoToLibrary(LibraryDto)));

        return libraries;
    }

    /**
     * Map a list of Library entities to a list of LibraryDto entities.
     * @param libraries
     * @return
     */
    private List<LibraryDto> mapLibrariesToLibrariesDTO(List<Library> libraries) {
        List<LibraryDto> librariesDto = new ArrayList<>();
        libraries.forEach(Library -> librariesDto.add(mapLibraryToLibraryDto(Library)));

        return librariesDto;
    }
}