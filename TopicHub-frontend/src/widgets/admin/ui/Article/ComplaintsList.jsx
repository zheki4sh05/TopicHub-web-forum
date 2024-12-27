import { Box, Stack } from "@mui/material";
import Complaint from "../../../../shared/Complaint/ui/Complaint";
import { useDispatch } from "react-redux";
import { useEffect } from "react";
import { delComplaint, fetchComplaints, rejectComplaint } from "../../api/requests";

function ComplaintsList({list}) {
    const dispatch = useDispatch()
    const handleReject=(id)=>{
        dispatch(
            rejectComplaint({
                type:"article",
                id:id
            })
        )
    }

    const handleDelete=(id)=>{
        dispatch(delComplaint({
            id:id
        }))
    }


    return ( 
    
    <Stack direction={"column"} spacing={2} sx={{marginTop:"15px"}}>

        {
            list.map((item,index)=>(
                <Box  key={index}>
                <Complaint
                   id={item.id} 
                   type={item.type}
                   name={item.title}
                   body={item.body}
                   article={item.articleDto}
                   handleReject={handleReject}
                   handleDelete={handleDelete}

                    />
                </Box>
            
            ))

        }

        

    </Stack>

);
}

export default ComplaintsList;