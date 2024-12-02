import { useDispatch, useSelector } from "react-redux";
import { createArticle, getSandboxList, getSandboxWords, getTheme, isEmpty } from "../../model/sandboxSlice";
import KeyWords from './../../../../entities/article/ui/KeyWords';
import { Button } from "@mui/material";

function SaveArticle() {
    const dispatch = useDispatch()
    const empty = useSelector(isEmpty);
        const theme  = useSelector(getTheme)
        const keyWords = useSelector(getSandboxWords)
        const list = useSelector(getSandboxList)

    const handleSave=()=>{
        dispatch(createArticle(
            theme,
            keyWords,
            list
        ))
    }

    return ( 

        <Button variant="contained" disabled={!empty} onClick={handleSave}>Опубликовать</Button>

     );
}

export default SaveArticle;