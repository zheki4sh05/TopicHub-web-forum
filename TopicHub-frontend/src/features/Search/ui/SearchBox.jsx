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
import { getUser, isAuth } from "../../../pages/Profile/model/userSlice";

function SearchBox() {
  const navigate = useNavigate()
  const [open, setOpen] = useState(false);
  const options = useSelector(getSearchOptions);
  const dispatch = useDispatch();
  const user = useSelector(getUser)
  const auth = useSelector(isAuth)

  const empty = useSelector(getSearchState)

  const makeRequest=(data)=>{
    if(!empty){
      dispatch(searchRequest( auth ? { ...data,user:user.id} : {...data} ));
      handleClose()
      navigate(PathConstants.SEARCH)
    }
  }

  const handleSearch = (event) => {
    event.preventDefault()
    makeRequest(options)
   
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
        <IconButton type="submit"  sx={{ p: "5px" }} aria-label="search">
          <SearchIcon />
        </IconButton>
      </Paper>

      <MainModal open={open} handleClose={handleClose}>
        <SearchFormBody onSearch={makeRequest} />
      </MainModal>
    </>
  );
}

export default SearchBox;
