import { Box, Button, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import ArticleTheme from "./../../../entities/article/ui/ArticleTheme";
import { articleTheme } from "../../../entities/article/api/createDefaultEntity";
import KeyWords from "../../../entities/article/ui/KeyWords";
import ArticleSanbox from "../../../features/Sanbox/ui/ArticleSanbox";
import CreateArticleHeader from './../../../widgets/createArticleHeader/ui/CreateArticleHeader';
import { useSelector } from "react-redux";
import { getTheme, isEmpty } from "../../../features/Sanbox/model/sandboxSlice";
import SaveArticle from "../../../features/Sanbox/ui/components/SaveArticle";

function CreateArticle() {

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


      
    </Box>
  );
}

export default CreateArticle;
