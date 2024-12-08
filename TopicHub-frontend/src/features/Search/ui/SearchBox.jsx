import { IconButton, Paper, TextField } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import { useState } from "react";
import SettingsIcon from "@mui/icons-material/Settings";

import MainModal from "../../../shared/ConfirmModal/ui/Modal";
import SearchFormBody from "../../../widgets/search/ui/SearchFormBody";
import { useDispatch, useSelector } from "react-redux";
import { getSearchOptions, getSearchState, setSearchOptions } from "../model/searchSlice";
import { searchRequest } from "./../api/request";
import { useNavigate } from "react-router";
import { PathConstants } from "../../../app/pathConstants";

function SearchBox() {
  const navigate = useNavigate()
  const [open, setOpen] = useState(false);
  const options = useSelector(getSearchOptions);
  const dispatch = useDispatch();

  const empty = useSelector(getSearchState)


  const onClick = () => {
    navigate(PathConstants.SEARCH)
    dispatch(searchRequest({ ...options }));
  };

  const handleSearch = () => {

    if(!empty){
      dispatch(searchRequest({ ...options }));
      handleClose()
      navigate(PathConstants.SEARCH)
    }
   
  };

  const handleSearchSettingsClick = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <>
      <Paper
        component="form"
        elevation={1}
        sx={{
          display: "flex",
          alignItems: "center",
          width: 350,
          bgcolor: "white",

          border: "#DCDCDC solid 2px",
          borderRadius: "10px",
        }}
        onSubmit={handleSearch}
      >
        <IconButton onClick={handleSearchSettingsClick}>
          <SettingsIcon />
        </IconButton>

        <TextField
          id="input"
          name="input"
          sx={{ flex: 1 }}
          siz="small"
          variant="standard"
          value={options.theme}
          onChange={(event) => dispatch(setSearchOptions({...options, theme:event.target.value }))}
        />
        <IconButton onClick={onClick} sx={{ p: "5px" }} aria-label="search">
          <SearchIcon />
        </IconButton>
      </Paper>

      <MainModal open={open} handleClose={handleClose}>
        <SearchFormBody onSearch={handleSearch} />
      </MainModal>
    </>
  );
}

export default SearchBox;
