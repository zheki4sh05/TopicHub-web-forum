import { Box } from "@mui/material";
import userSlice, { getUser } from './../../Profile/model/userSlice';
import statusTypes from "../../../app/util/statusTypes";
import { useNavigate } from "react-router";
import { useEffect } from "react";
import { PathConstants } from "../../../app/pathConstants";
import { useSelector } from "react-redux";
import AdminMenu from "../../../widgets/admin/ui/AdminMenu";

function ManageArticle() {

    const navigate = useNavigate();

    const user = useSelector(getUser);

    useEffect(()=>{

        if(!user.roles.includes(statusTypes.admin)){
            navigate(PathConstants.ARTICLE)
        }
    
    },[])

    return (
        <Box>
            <AdminMenu/>

        </Box>
    )

    
    
}

export default ManageArticle;