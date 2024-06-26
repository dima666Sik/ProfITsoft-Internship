
import * as pages from "./pages";
import config from "config";

const result = {
	[pages.defaultPage]: `${config.UI_URL_PREFIX}/${pages.defaultPage}`,
	[pages.login]: `${config.UI_URL_PREFIX}/${pages.login}`,
	[pages.secretPage]: `${config.UI_URL_PREFIX}/${pages.secretPage}`,
	[pages.listEntitiesPage]: `${config.UI_URL_PREFIX}/${pages.listEntitiesPage}`,
	[pages.detailEntityPage]: `${config.UI_URL_PREFIX}/${pages.detailEntityPage}`,
};

export default result;
