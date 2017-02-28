package org.lendi.umtapo.service.specific;

import org.lendi.umtapo.dto.LibraryDto;
import org.lendi.umtapo.entity.Library;
import org.lendi.umtapo.service.generic.GenericService;
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
     * Find one by borrower id library.
     *
     * @param borrowerId the borrower id
     * @return the library
     */
    Library findOneByBorrowerId(Integer borrowerId);

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
