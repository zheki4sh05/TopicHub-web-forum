import { useDispatch, useSelector } from "react-redux";
import { isAuth } from "../../Profile/model/userSlice";
import SandboxContainer from "../../../features/Sanbox/ui/SandboxContainer";
import { createArticle } from "../../../features/Sanbox/api/requests";

function CreateArticle() {
  const auth = useSelector(isAuth);
  const dispatch = useDispatch()
  const saveHandler=({theme, keyWords,list, hub })=>{
    dispatch(
      createArticle({theme, keyWords,list, hub })
    );
  }
  return (
    // <Box
    //   sx={{
    //     display: "flex",
    //     flexDirection: "column",
    //     maxWidth: "1000px",
    //     margin: "0 auto",
    //     gap: "10px",
    //   }}
    // >
    //   {auth ? (
    //     <>
    //       <MenuWrapper>
    //         <CreateArticleHeader />
    //       </MenuWrapper>
    //       <Box sx={{ width: "820px", margin: "0 auto" }}>
    //         <MenuWrapper>
    //           <ArticleTheme/>
    //         </MenuWrapper>
    //       </Box>

    //       <Box sx={{ width: "820px", margin: "0 auto" }}>
    //         <MenuWrapper>
    //           <KeyWords />
    //         </MenuWrapper>
    //       </Box>

    //       <Box sx={{ width: "820px", margin: "0 auto" }}>
    //         <ArticleSanbox />
    //       </Box>
    //       <Box
    //         sx={{
    //           display: "flex",
    //           justifyContent: "flex-end",
    //           marginTop: "10px",
    //         }}
    //       >
    //         <SaveArticle />
    //       </Box>
    //     </>
    //   ) : (
    //     <MenuWrapper>
    //       <Stack direction={"row"} sx={{ alignItems: "center" }}>
    //         <Typography variant="body1">
    //           Для публикации статей необходимо авторизоваться
    //         </Typography>
    //         <Button
    //           sx={{ marginLeft: "20px" }}
    //           variant="outlined"
    //           onClick={() => navigate(PathConstants.LOGIN)}
    //         >
    //           Авторизация
    //         </Button>
    //       </Stack>
    //     </MenuWrapper>
    //   )}
    // </Box>
    <SandboxContainer
    
    auth={auth}
    actionHandler={saveHandler}
    
    />
  );
}

export default CreateArticle;
