import {
  Box,
  Button,
  Drawer,
  Stack,
  Typography,
} from "@mui/material";
import MenuWrapper from "../../../widgets/menu/ui/MenuWrapper";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import { useState } from "react";
import MainRadio from "../../../shared/Radio/ui/MainRadio";
import { DateCalendar } from "@mui/x-date-pickers";
import dayjs from "dayjs";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import CloseIcon from "@mui/icons-material/Close";
import { useTranslation } from "react-i18next";
function ArticleFilterBar({filterAction,defaultRequest}) {
  const rating = [
    {
      id: "10",
      name: ">10%",
    },
    {
      id: "30",
      name: ">30%",
    },
    {
      id: "50",
      name: ">50%",
    },
    {
      id: "70",
      name: ">70%",
    },
    {
      id: "90",
      name: ">90%",
    },
  ];
  const [open, setOpen] = useState(false);
  const [date,setDate] = useState(dayjs(new Date()))
  const [ratValue,setRatingValue] = useState(rating[rating.length-1].id)
const { t } = useTranslation();
  
  const toggleDrawer = (newOpen) => () => {
    setOpen(newOpen);
  };
  const handleFilter=()=>{
    filterAction(
      {
        month:date.month()+1,
        year:date.year(),
        rating:ratValue
      }
    )
    setOpen(!open)
  }
  const dateChange=(value)=>{
    setDate(value)
  }
  const ratingChange=(value)=>{
    setRatingValue(value)
  }
  return (
    <>
      <Box
        sx={{
          marginTop: "70px",
          position: "absolute",
          top: "10%",
          left: "10px",
          width: "auto",
        }}
      >
        <MenuWrapper>
          <Button
            variant="outlined"
            startIcon={
               <KeyboardArrowDownIcon />
            }
            onClick={() => setOpen(!open)}
          >
              {t('btn_filter')}
          </Button>
        </MenuWrapper>
      </Box>

      <Drawer open={open} onClose={toggleDrawer(false)}>
        <MenuWrapper>
         
          <Stack direction="column">
            <Box>
            <Button
            variant="outlined"
            startIcon={<CloseIcon />}
            onClick={() => setOpen(!open)}
          >
            {t('btn_close')}
          </Button>
            </Box>
         
      
              <Stack direction="column" sx={{ marginTop: "5px" }}>
                <Stack direction="column">
                  <Typography> {t('txt_filter_1')}</Typography>
                  <LocalizationProvider dateAdapter={AdapterDayjs}>
                    <DateCalendar
                      onChange={dateChange}
                      value={date}
                      views={["month", "year"]}
                      openTo="month"
                    />
                  </LocalizationProvider>
                </Stack>
                <Stack direction="column">
                  <Typography> {t('txt_filter_2')}</Typography>
                  <MainRadio list={rating} defaultValue={ratValue} changeHandler={ratingChange}/>
                </Stack>
                <Stack direction="row" sx={{justifyContent:"space-between"}}>
                  <Button variant="outlined" color="secondary" onClick={()=> {setOpen(!open); defaultRequest(1)}}>
                  {t('btn_reset')}
                  </Button>
                <Button variant="outlined" color="success" onClick={handleFilter}>
                {t('btn_confirm')}
                </Button>
                </Stack>
                
              </Stack>
    
          </Stack>
        </MenuWrapper>
      </Drawer>
    </>
  );
}
export default ArticleFilterBar;
