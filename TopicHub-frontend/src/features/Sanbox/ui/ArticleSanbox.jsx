import { Box } from "@mui/material";
import SandboxSelect from "./SandboxSelect";
import { useDispatch, useSelector } from "react-redux";
import { getSandboxList } from "../model/sandboxSlice";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import ParagraphEdit from "./components/ParagraphEdit";

function ArticleSanbox() {

    const list = useSelector(getSandboxList)
 

    const getItemByType=(item,index)=>{
     
        switch(item.type){
            case "paragraph":{
                return (<ParagraphEdit item={item} key={index} />)
            }
        
        }
    }

    return ( 
    <Box
    
    sx={{
        
        display:"flex",
        flexDirection:"column"
        
    }}
    
    >
    <Box sx={{display:"flex", flexDirection:"column"}} >
        
      <Box sx={{width: "820px", margin: "0 auto", marginBottom:"20px" }}>
        <MenuWrapper>
        <Box sx={{display:"flex", flexDirection:"column",width:"100%"}}>

        {
            list.map((item,index)=>(
                getItemByType(item, index)
            ))
        }

       </Box>
        
 
       
         
        </MenuWrapper>
      </Box>

        <SandboxSelect/>

    </Box>


    </Box> );
}

export default ArticleSanbox;