import { Box, Button, LinearProgress, Typography } from "@mui/material";
import statusTypes from "../../../app/util/statusTypes";
import Article from "../../../features/Article/ui/Article";

function ArticlesList({status, batch ,makeRequest,select, edit=false}) {
    return ( 


        <Box sx={{ margin: "20px 0 20px 0" }}>
        {status == statusTypes.loading ? (
          <Box sx={{ width: "100%" }}>
            <LinearProgress />
          </Box>
        ) : status == statusTypes.succeeded || batch.articleDtoList.length>0 ? (
          <>
            <Box sx={{ maxWidth: "820px", margin: "0 auto" }}>
              {batch.articleDtoList.map((item, index) => (
                <Box key={index} sx={{marginBottom:"10px"}}>
                  <Article  item={item} mode={statusTypes.short} edit={edit}/>
                </Box>
              ))}
            </Box>
          </>
        ) : (
          <Box sx={{ display: "flex", flexDirection: "row" }}>
            <Typography variant="subtitle1" gutterBottom sx={{ color: "red" }}>
              Ошибка загрузки!
            </Typography>
            <Button
              sx={{ marginLeft: "15px" }}
              variant="outlined"
              onClick={() => makeRequest(select, 1)}
            >
              Попробовать еще раз
            </Button>
          </Box>
        )}
      </Box>

     );
}

export default ArticlesList;