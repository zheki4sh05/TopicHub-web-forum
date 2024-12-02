import { Box, Button, Typography } from "@mui/material";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { resetSandBox } from "../../../features/Sanbox/model/sandboxSlice";
import ConfirmModal from "../../../shared/ConfirmModal/ui/ConfirmModal";

function CreateArticleHeader() {

    const [open,setOpen] = useState(false)
    const dispatch = useDispatch()
  const handleClear = () => {

    setOpen(true)

  };
    const handlerAgree=()=>{
        dispatch(resetSandBox())
        setOpen(false)
    }
    const handlerDisagree=()=>{
        setOpen(false)

    }
  return (
    <>
      <Box
        sx={{
          display: "flex",
          justifyContent: "space-between",
          width: "100%",
        }}
      >
        <Typography variant="h6">Создание статьи</Typography>
        <Button
          size="small"
          variant="outlined"
          color="error"
          onClick={handleClear}
        >
          Сбросить
        </Button>
      </Box>

      <ConfirmModal
        show={open}
        title={"Удаление шаблона статьи"}
        body={"Вы действительно хотите удалить все?"}
        data={{}}
        handlerAgree={handlerAgree}
        handlerDisagree={handlerDisagree}
      />
    </>
  );
}

export default CreateArticleHeader;
