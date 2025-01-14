import { Box, Button, Stack, Typography } from "@mui/material";
import { PathConstants } from "../../../app/pathConstants";
import { useNavigate } from "react-router";
import SaveArticle from "./components/SaveArticle";
import KeyWords from "../../../entities/article/ui/KeyWords";
import ArticleTheme from '../../../entities/article/ui/ArticleTheme';
import MenuWrapper from '../../../widgets/menu/ui/MenuWrapper';
import CreateArticleHeader from '../../../widgets/createArticleHeader/ui/CreateArticleHeader';
import ArticleSanbox from './ArticleSanbox';

function SandboxContainer({auth, actionHandler}) {
  const navigate = useNavigate();
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
      {auth ? (
        <>
          <MenuWrapper>
            <CreateArticleHeader />
          </MenuWrapper>
          <Box sx={{ width: "820px", margin: "0 auto" }}>
            <MenuWrapper>
              <ArticleTheme/>
            </MenuWrapper>
          </Box>

          <Box sx={{ width: "820px", margin: "0 auto" }}>
            <MenuWrapper>
              <KeyWords />
            </MenuWrapper>
          </Box>

          <Box sx={{ width: "820px", margin: "0 auto" }}>
            <ArticleSanbox />
          </Box>
          <Box
            sx={{
              display: "flex",
              justifyContent: "flex-end",
              marginTop: "10px",
            }}
          >
            <SaveArticle action={actionHandler}/>
          </Box>
        </>
      ) : (
        <MenuWrapper>
          <Stack direction={"row"} sx={{ alignItems: "center" }}>
            <Typography variant="body1">
              Для публикации статей необходимо авторизоваться
            </Typography>
            <Button
              sx={{ marginLeft: "20px" }}
              variant="outlined"
              onClick={() => navigate(PathConstants.LOGIN)}
            >
              Авторизация
            </Button>
          </Stack>
        </MenuWrapper>
      )}
    </Box>
      
    );
}

export default SandboxContainer;