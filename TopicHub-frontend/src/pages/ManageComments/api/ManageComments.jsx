import { Box } from "@mui/material";
import ComplaintsList from "./../../../widgets/admin/ui/Article/ComplaintsList";
import AdminMenu from "../../../widgets/admin/ui/AdminMenu";

function ManageComments() {
  return (
    <Box>
      <AdminMenu />
      <Box sx={{ maxWidth: "1000px", margin: "0 auto", marginTop: "10px" }}>
        <ComplaintsList />
      </Box>
    </Box>
  );
}

export default ManageComments;
