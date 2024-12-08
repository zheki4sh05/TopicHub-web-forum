import { Box, Button, Stack, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import ArticleTheme from "./../../../entities/article/ui/ArticleTheme";
import { articleTheme } from "../../../entities/article/api/createDefaultEntity";
import KeyWords from "../../../entities/article/ui/KeyWords";
import ArticleSanbox from "../../../features/Sanbox/ui/ArticleSanbox";
import CreateArticleHeader from './../../../widgets/createArticleHeader/ui/CreateArticleHeader';
import { useSelector } from "react-redux";
import SaveArticle from "../../../features/Sanbox/ui/components/SaveArticle";
import { isAuth } from "../../Profile/model/userSlice";
import { useNavigate } from "react-router";
import { PathConstants } from "../../../app/pathConstants";

function CreateArticle() {
  const navigate  = useNavigate()

  const auth = useSelector(isAuth)

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        maxWidth: "1000px",
        margin: "0 auto",
        gap: "10px",
      }}
    >

    {

      auth ? 
      <>
      
      <MenuWrapper>
        <CreateArticleHeader/>
      </MenuWrapper>
      <Box sx={{width: "820px", margin: "0 auto" }}>
        <MenuWrapper>
          <ArticleTheme data={articleTheme} />
        </MenuWrapper>
      </Box>

      <Box sx={{width: "820px", margin: "0 auto" }}>
        <MenuWrapper>
          <KeyWords />
        </MenuWrapper>
      </Box>

      <Box sx={{width: "820px", margin: "0 auto" }}>
        <ArticleSanbox/>
      </Box>
      <Box sx={{display:"flex",justifyContent:"flex-end",marginTop:"10px"}} >
        <SaveArticle/>
      </Box>
      
      </>

      : 
      <MenuWrapper>
<Stack direction={"row"} sx={{alignItems:"center",}}>
      <Typography variant="body1" >Для публикации статей необходимо авторизоваться</Typography>
      <Button sx={{marginLeft:"20px"}} variant="outlined" onClick={()=>navigate(PathConstants.LOGIN)}>Авторизация</Button>
      
      </Stack>
      </MenuWrapper>

      

    }

      


      
    </Box>
  );
}

export default CreateArticle;
