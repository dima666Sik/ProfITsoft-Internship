import {Provider} from 'react-redux';
import React, {useMemo} from 'react';

import rootReducer from './reducers';
import IntlProvider from "../../misc/providers/IntlProvider";
import useLocationSearch from "../../misc/hooks/useLocationSearch";
import getMessages from "./intl";
import configureStore from "../../misc/redux/configureStore";
import DetailPlanet from "./containers/DetailPlanet";

const store = configureStore(rootReducer);
export default function Index() {
    const {
        lang,
    } = useLocationSearch();
    const messages = useMemo(() => getMessages(lang), [lang]);
    return (
        <IntlProvider messages={messages}>
            <Provider store={store}>
                <DetailPlanet/>
            </Provider>
        </IntlProvider>
    );
}