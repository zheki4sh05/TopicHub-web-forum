import { useState } from "react";
import Backdrop from '@mui/material/Backdrop';
import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Fade from '@mui/material/Fade';
import { IconButton } from "@mui/material";
import CloseIcon from '@mui/icons-material/Close';
const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
    borderRadius:"5px"
  };

function MainModal({children,open, handleClose}) {


    return (

        <div>
        <Modal
          aria-labelledby="transition-modal-title"
          aria-describedby="transition-modal-description"
          open={open}
          onClose={handleClose}
          closeAfterTransition
          slots={{ backdrop: Backdrop }}
          slotProps={{
            backdrop: {
              timeout: 450,
            },
          }}
        >
          <Fade in={open}>
            <Box sx={style}>
                <Box sx={{display:"flex", justifyContent:"flex-end"}} >

                <IconButton onClick={handleClose} >
                    <CloseIcon/>
                </IconButton>

                </Box>
             {children}
            </Box>
          </Fade>
        </Modal>
      </div>


      );
}

export default MainModal;