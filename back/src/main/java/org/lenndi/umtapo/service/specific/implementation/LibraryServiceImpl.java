package org.lenndi.umtapo.service.specific.implementation;

import org.lenndi.umtapo.dao.ItemDao;
import org.lenndi.umtapo.dao.LibraryDao;
import org.lenndi.umtapo.dto.LibraryDto;
import org.lenndi.umtapo.entity.Item;
import org.lenndi.umtapo.entity.Library;
import org.lenndi.umtapo.enumeration.Condition;
import org.lenndi.umtapo.enumeration.ItemType;
import org.lenndi.umtapo.mapper.LibraryMapper;
import org.lenndi.umtapo.service.generic.AbstractGenericService;
import org.lenndi.umtapo.service.specific.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Library service implementation.
 */
@Service
public class LibraryServiceImpl extends AbstractGenericService<Library, Integer> implements LibraryService {

    private final LibraryMapper libraryMapper;
    private final LibraryDao libraryDao;
    private final ItemDao itemDao;

    /**
     * Instantiates a new Library service.
     *
     * @param libraryMapper the library mapper
     * @param libraryDao    the library dao
     * @param itemDao       the item dao
     */
    @Autowired
    public LibraryServiceImpl(LibraryMapper libraryMapper, LibraryDao libraryDao, ItemDao itemDao) {
        this.libraryMapper = libraryMapper;
        this.libraryDao = libraryDao;
        this.itemDao = itemDao;
    }

    @Override
    @Transactional
    public LibraryDto createLibrary(LibraryDto libraryDto) {
        libraryDto = this.saveDto(libraryDto);

        Item item = new Item();
        item.setInternalId(libraryDto.getFirstInternalId());
        item.setLibrary(this.libraryMapper.mapLibraryDtoToLibrary(libraryDto));
        item.setLoanable(false);
        item.setType(ItemType.INITIAL_ITEM);
        item.setBorrowed(false);
        item.setCondition(Condition.SOLD);
        itemDao.save(item);

        return libraryDto;
    }

    @Override
    public LibraryDto saveDto(LibraryDto libraryDto) {
        Library library = this.libraryMapper.mapLibraryDtoToLibrary(libraryDto);
        library = this.save(library);

        return this.libraryMapper.mapLibraryToLibraryDto(library);
    }

    @Override
    public LibraryDto findOneDto(Integer id) {
        Library library = this.findOne(id);

        return this.libraryMapper.mapLibraryToLibraryDto(library);
    }

    @Override
    public List<LibraryDto> findAllDto() {
        return mapLibrariesToLibrariesDTO(this.findAll());
    }

    @Override
    public List<LibraryDto> findAllExternal() {
        return this.mapLibrariesToLibrariesDTO(this.libraryDao.findByExternalTrue());
    }

    @Override
    public List<LibraryDto> findAllPartner() {
        return this.mapLibrariesToLibrariesDTO(this.libraryDao.findByExternalFalse());
    }

    @Override
    public Library mapLibraryDtoToLibrary(LibraryDto libraryDto) {
        return this.libraryMapper.mapLibraryDtoToLibrary(libraryDto);
    }


    private LibraryDto mapLibraryToLibraryDto(Library library) {
        return this.libraryMapper.mapLibraryToLibraryDto(library);
    }

    private List<Library> mapLibrariesDtoToLibraries(List<LibraryDto> librariesDto) {
        List<Library> libraries = new ArrayList<>();
        librariesDto.forEach(LibraryDto -> libraries.add(mapLibraryDtoToLibrary(LibraryDto)));

        return libraries;
    }

    private List<LibraryDto> mapLibrariesToLibrariesDTO(List<Library> libraries) {
        List<LibraryDto> librariesDto = new ArrayList<>();
        libraries.forEach(Library -> librariesDto.add(mapLibraryToLibraryDto(Library)));

        return librariesDto;
    }
}
