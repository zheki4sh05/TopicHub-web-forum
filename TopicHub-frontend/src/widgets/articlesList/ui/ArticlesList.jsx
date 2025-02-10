import { Box, Button, LinearProgress, Typography } from "@mui/material";
import statusTypes from "../../../app/util/statusTypes";
import Article from "../../../features/Article/ui/Article";
import { useTranslation } from "react-i18next";


function ArticlesList({ status, batch, makeRequest, edit = false }) {
  const { t } = useTranslation();
  return (
    <Box sx={{ margin: "20px 0 20px 0" }}>
      {status == statusTypes.loading ? (
        <Box sx={{ width: "100%" }}>
          <LinearProgress />
        </Box>
      ) : status == statusTypes.succeeded || batch.items.length > 0 ? (
        <>
          <Box sx={{ maxWidth: "820px", margin: "0 auto" }}>
            {batch.items.map((item) => (
              <Box key={item.id} sx={{ marginBottom: "10px" }}>
                <Article
                  key={item.id}
                  item={item}
                  mode={statusTypes.short}
                  edit={edit}
                />
              </Box>
            ))}
          </Box>
        </>
      ) : status == statusTypes.failed ? (
        <Box sx={{ display: "flex", flexDirection: "row" }}>
          <Typography variant="subtitle1" gutterBottom sx={{ color: "red" }}>
           {t('txt_error_http')}
          </Typography>
          <Button
            sx={{ marginLeft: "15px" }}
            variant="outlined"
            onClick={() => makeRequest(1)}
          >
            {t('btn_repeat')}
          </Button>
  
        </Box>
      ) : (
        <></>
      )}
    </Box>
  );
}

export default ArticlesList;
