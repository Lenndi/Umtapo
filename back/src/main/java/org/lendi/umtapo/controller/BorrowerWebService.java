package org.lendi.umtapo.controller;

import org.lendi.umtapo.service.specific.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by axel on 05/12/16.
 */
@RestController
public class BorrowerWebService {

 @Autowired
 private BorrowerService borrowerService;

}
