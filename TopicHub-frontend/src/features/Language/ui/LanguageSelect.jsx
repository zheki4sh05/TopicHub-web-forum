import { useDispatch, useSelector } from "react-redux";
import MainSelect from "../../../shared/Select/ui/MainSelect";
import { getActiveLanguage, getLanguagesList, setLanguage } from "../../../processes/header/model/settingsSlice";
import { Box } from "@mui/material";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";

function LanguageSelect() {
  const languages = useSelector(getLanguagesList)
  const dispatch = useDispatch();
  const { i18n  } = useTranslation();
  const {t} = useTranslation()
  const [state,setState] = useState(languages[0].id)
  const handleChange = (id) => {
    setState(id)
    const code = languages.find(item=>item.id==id).code
    dispatch(setLanguage(code));
    i18n.changeLanguage(code)
  };

  return (
    <Box sx={{maxWidth:"120px"}}>
      <MainSelect
        title={t('message_lang')}
        list={languages}
        defaultValue={state}
        handleChange={handleChange}
      />
    </Box>
  );
}

export default LanguageSelect;
