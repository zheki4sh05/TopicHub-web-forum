import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import HttpBackend from 'i18next-http-backend';

import translationEN from "./en/translation.json";
import translationRU from "./ru/translation.json";

const resources = {
  en: {
    translation: translationEN
  },
  ru: {
    translation: translationRU
  }
};

i18n
  .use(HttpBackend)
  .use(initReactI18next)
  .init({
    fallbackLng: 'ru',
    debug: true,
    resources,
    interpolation: {
      escapeValue: false
    }
  });

export default i18n;