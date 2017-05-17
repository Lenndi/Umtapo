package org.lenndi.umtapo.service.specific;

import org.lenndi.umtapo.dto.LibraryDto;
import org.lenndi.umtapo.entity.Library;
import org.lenndi.umtapo.service.generic.GenericService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Library service.
 */
@Service
public interface LibraryService extends GenericService<Library, Integer> {

    /**
     * {@inheritDoc}
     */
    @Override
    Library save(Library library);

    /**
     * Persist a Library from a LibraryDto.
     *
     * @param libraryDto the library dto
     * @return library dto
     */
    LibraryDto saveDto(LibraryDto libraryDto);

    /**
     * Create a library.
     *
     * @param libraryDto the library dto
     * @return library dto
     */
    LibraryDto createLibrary(LibraryDto libraryDto);

    /**
     * {@inheritDoc}
     */
    @Override
    Library findOne(Integer id);

    /**
     * Find a Library by id.
     *
     * @param id the id
     * @return LibraryDto library dto
     */
    LibraryDto findOneDto(Integer id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<Library> findAll();

    /**
     * Find all Libraries.
     *
     * @return list list
     */
    List<LibraryDto> findAllDto();

    /**
     * Find all external list.
     *
     * @return the list
     */
    List<LibraryDto> findAllExternal();

    /**
     * Find all partner list.
     *
     * @return the list
     */
    List<LibraryDto> findAllPartner();

    /**
     * Transform LibraryDto to Library.
     *
     * @param libraryDto library dto
     * @return library entity
     */
    Library mapLibraryDtoToLibrary(LibraryDto libraryDto);

    /**
     * {@inheritDoc}
     */
    @Override
    Boolean exists(Integer id);
}
