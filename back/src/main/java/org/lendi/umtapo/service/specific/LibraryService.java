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
     * Persist a Library.
     *
     * @param library
     * @return LibraryDto
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
     * Find a Library by id.
     *
     * @param id
     * @return LibraryDto
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
     * Find all Libraries.
     *
     * @return List<LibraryDto>
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
     * Check if Library exist.
     *
     * @return true if exist.
     */
    @Override
    Boolean exists(Integer id);
}
