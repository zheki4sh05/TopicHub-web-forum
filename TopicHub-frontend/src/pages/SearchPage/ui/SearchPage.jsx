import { useDispatch, useSelector } from "react-redux";
import { cleareSearch, getSearchResult, getSearchStatus } from "../../../features/Search/model/searchSlice";
import ArticlesList from "../../../widgets/articlesList/ui/ArticlesList";
import { Box, Button, Typography } from "@mui/material";

function SearchPage() {

    const searchResult = useSelector(getSearchResult)
    const status = useSelector(getSearchStatus)
    const dispatch = useDispatch(getSearchResult)

    const searchReques=(select, page)=>{

    }

    const handleReset=()=>{
        dispatch(cleareSearch())
    }

    return ( 
            <Box sx={{maxWidth:"1000px",margin:"0 auto"}} >

                <Box sx={{display:"flex", justifyContext:"flex-end"}} >

                <Button variant="outlined" color="secondary" onClick={handleReset}>
                    Сбросить
                </Button>
            

                </Box>
                {
                
                Object.keys(searchResult).length>0 ? 
                <ArticlesList
                status={status}
                batch={searchResult}
                makeRequest={searchReques}
                select={1}
                edit={false}
                />
                :
                null

                }
               
            </Box>
    
     );
}

export default SearchPage;