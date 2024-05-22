import languages from 'misc/constants/languages';
import mixMessages from 'misc/intl/messages';
const DEFAULT_LANG = languages.en;

function getMessages(lang) {
  const planetInfo = require('./planetInfo.json');
  let info;
  try {
    info = lang === DEFAULT_LANG
      ? planetInfo
      : require(`./planetInfo.${lang.toLowerCase()}.json`);
  } catch (e) {
    info = planetInfo;
  }
  return mixMessages({
    planetInfo,
    info,
  });
}

export default getMessages;
