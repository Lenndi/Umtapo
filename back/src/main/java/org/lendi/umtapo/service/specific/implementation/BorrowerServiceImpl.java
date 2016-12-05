package org.lendi.umtapo.service.specific.implementation;


import org.lendi.umtapo.dao.UserDao;
import org.lendi.umtapo.entity.Borrower;
import org.lendi.umtapo.entity.User;
import org.lendi.umtapo.service.generic.implementation.GenericServiceImpl;
import org.lendi.umtapo.service.specific.BorrowerService;
import org.lendi.umtapo.service.specific.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by axel on 29/11/16.
 */
@Service
public class BorrowerServiceImpl extends GenericServiceImpl<Borrower, Integer> implements BorrowerService {

 @Autowired
 private UserDao userDao;
}
