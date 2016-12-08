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
    Library save(Library library);

    /**
     * Persist a Library from a LibraryDto.
     *
     * @param libraryDto
     * @return
     */
    LibraryDto save(LibraryDto libraryDto);

    /**
     * Find a Library by id.
     *
     * @param id
     * @return LibraryDto
     */
    Library find(Integer id);

    /**
     * Find a Library by id.
     *
     * @param id
     * @param isDto Return a LibraryDto entity if true.
     * @return LibraryDto
     */
    LibraryDto find(Integer id, boolean isDto);

    /**
     * Find all Libraries.
     *
     * @return List<LibraryDto>
     */
    List<Library> findAll();

    /**
     * Find all Libraries.
     *
     * @param isDto Return a LibraryDto if true.
     * @return
     */
    List<LibraryDto> findAll(boolean isDto);

    /**
     * Check if Library exist.
     *
     * @param library
     * @return true if exist.
     */
    Boolean exists(Library library);

    /**
     * Check if Library exist.
     *
     * @param libraryDto
     * @return true if exist.
     */
    Boolean exists(LibraryDto libraryDto);
}