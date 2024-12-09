import { Box, Button, Typography } from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import { PathConstants } from "../../../app/pathConstants";
import { Link } from "react-router";
import { useSelector } from "react-redux";
import { getArticle } from "../../../features/Article/model/articleSlice";
import Article from "../../../features/Article/ui/Article";
import CommentsList from "../../../widgets/comments/ui/CommentsList";



function ArticleView() {

    const article = useSelector(getArticle)



  return (
    <Box 
    
    sx={{
        display: "flex",
        flexDirection: "column",
        maxWidth: "1000px",
        margin: "0 auto",
      }}
    
    >
      <MenuWrapper>
        <Box
          sx={{
            display: "flex",
            justifyContent: "flex-start",
            alignItems: "center",
            width: "100%",
          }}
        >
          <Link to={PathConstants.ARTICLE}>
          
              <Typography
                variant="body1"
                style={{ textDecoration:"underline" }}
              >
                Назад
              </Typography>
            
          </Link>
        </Box>
      </MenuWrapper>
      

      <Box sx={{margin:"20px 0 20px 0"}} >
            <Box sx={{maxWidth:"820px", display:"flex", flexDirection:"column", margin:"0 auto",gap:"10px"}} >
                <Article item={article} mode={"long"}/>

                <MenuWrapper>
                    <Box sx={{display:"flex", justifyContent:"space-between",alignItems:"center",width:"100%"}}>
                        <Box   sx={{display:"flex", flexDirection:"row", gap:"10px"}}>

                        <img alt="лого" />
                        <Typography>{article.userDto.login}</Typography>

                        </Box>
                    <Button variant="outlined" color="success">Подписаться</Button>
                    </Box>
                </MenuWrapper>
                
                <MenuWrapper>
                   <CommentsList
                   
                   article={{}}
                   
                   />
                </MenuWrapper>

            </Box>

      </Box>

     
     
    </Box>
  );
}

export default ArticleView;
