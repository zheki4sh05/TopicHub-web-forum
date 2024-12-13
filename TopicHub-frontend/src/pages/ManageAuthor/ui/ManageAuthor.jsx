import { Box, Button, IconButton, Paper, Stack, Switch, TextField, Typography } from "@mui/material";
import userSlice, { getUser, isAuth } from "./../../Profile/model/userSlice";
import statusTypes from "../../../app/util/statusTypes";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { PathConstants } from "../../../app/pathConstants";
import { useDispatch, useSelector } from "react-redux";
import AdminMenu from "../../../widgets/admin/ui/AdminMenu";

import Author from "../../../widgets/author/ui/Author";

import SearchIcon from "@mui/icons-material/Search";
import { blockAuthors, delAuthors, fetchAuthors } from "../api/requests";
import { getAuthors } from "../../../widgets/admin/model/adminSlice";

function ManageAuthor() {
  const navigate = useNavigate();
  const user = useSelector(getUser);
  const auth = useSelector(isAuth)
  const [value,setValue] = useState("");
  const dispatch = useDispatch()

  const authors = useSelector(getAuthors)
  const [list,setList] = useState(authors)
  useEffect(() => {
    
    if (!auth || !user.roles.includes(statusTypes.admin)) {
      navigate(PathConstants.ARTICLE);
    }else{
      dispatch(fetchAuthors({type:"authors"}))
    }
  }, []);
  
  const onClick=()=>{
   
  }

  const blockHandler=(id)=>{
      dispatch(blockAuthors({
        id:id
      }
      ))
  }

  const delAuthorHandler=(id)=>{
  
    dispatch(
      delAuthors(
        {
          authorId:id
        }
      )
    )
  }

  useEffect(()=>{
    console.log(value)
    if(value.length!=0){
      setList(list.filter(item=>item.login.startsWith(value)))
    }else{
      setList(authors)
    }

  },[value])
  
  return (
    <Box>
      <AdminMenu />
      <Box sx={{ maxWidth: "1000px", margin: "0 auto" }}>
        <Stack direction={"row"} sx={{alignItems:"center",padding:"15px"}}>
            {/* <Typography>Заблокированные</Typography>
        <Switch /> */}

        </Stack>

        <Paper
       
        elevation={1}
        sx={{
          display: "flex",
          alignItems: "center",
          width: 350,
          bgcolor: "white",

          border: "#DCDCDC solid 2px",
          borderRadius: "10px",
          margin:"0 auto"
        }}
       
      >


        <TextField
          id="input"
          name="input"
          sx={{ flex: 1 }}
          siz="small"
          variant="standard"
          value={value}
          onChange={(event) => setValue(event.target.value)}
        />
        
      </Paper>


     
      <Stack direction="column">
            <Typography sx={{marginBottom:"15px"}} >Всего: {authors.length}</Typography>
        {list.map((item, index) => (
          <Box
            key={index}
            sx={{
              display: "flex",
              justifyContent: "space-between",
              marginBottom: "15px",
              alignItems: "center",
              width: "100%",
            }}
          >
            <Author user={item} edit={false} size={50} />
            <Box sx={{display:"flex", flexDirection:"row", gap:"15px"}} >
             <Button variant="contained" color="error" onClick={()=>blockHandler(item.id)} > {item.status ? "Разблокировать" : "Заблокировать" }</Button>
             <Button variant="contained" color="warning" onClick={()=>delAuthorHandler(item.id)} >Удалить</Button>
            </Box>
          </Box>
        ))}
      </Stack>
       
      </Box>
    </Box>
  );
}

export default ManageAuthor;
