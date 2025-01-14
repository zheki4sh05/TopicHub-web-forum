import { useDispatch, useSelector } from "react-redux";
import MainSelect from "../../../shared/Select/ui/MainSelect";
import { getActiveLanguage, getLanguagesList, setLanguage } from "../../../processes/header/model/settingsSlice";
import { Box } from "@mui/material";

function LanguageSelect() {
  const languages = useSelector(getLanguagesList)
  const activeLanguage = useSelector(getActiveLanguage)
  const dispatch = useDispatch();
  
  const handleChange = (id) => {
    dispatch(setLanguage(id));
  };

  return (
    <Box sx={{maxWidth:"120px"}}>
      <MainSelect
        title="Язык"
        list={languages}
        defaultValue={activeLanguage}
        handleChange={handleChange}
      />
    </Box>
  );
}

export default LanguageSelect;
