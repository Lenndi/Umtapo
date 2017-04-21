package org.lenndi.umtapo.util;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

/**
 * JsonViewResolver.
 * <p>
 * Created by axel on 03/01/17.
 */
@Component
public class JsonViewResolver {

    /**
     * The interface Borrower search view.
     */
    public interface BorrowerSearchView {
    }

    /**
     * Dynamic mapping mapping jackson value.
     *
     * @param jsonView the json view
     * @param object   the object
     * @return the mapping jackson value
     */
    public MappingJacksonValue dynamicMapping(String jsonView, Object object) {
        MappingJacksonValue wrapper = new MappingJacksonValue(object);

        if (jsonView.equals("BorrowerSearchView")) {
            wrapper.setSerializationView(BorrowerSearchView.class);
        } else {
            wrapper = null;
        }
        return wrapper;
    }
}
