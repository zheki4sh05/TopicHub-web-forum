import { Box, Button, Skeleton, Stack, Typography } from "@mui/material";
import api from "../../../app/util/apiPath";
import { useEffect, useState } from "react";
import axios from "axios";
import getRequestConfig from "../../../app/util/requestConfig";
import getRequestImageConfig from "./../../../app/util/requestImageConfig";
import addParams from "../../../app/util/paramsConfig";
import { Link } from "react-router";

function Author({ user, edit = false, size = 100 }) {
  const [imageData, setImageData] = useState(null);
  const [input, setInput] = useState(false);

  const handleGetImage = async () => {
    try {
      const response = await axios.get(
        api.image.url.concat(addParams({ id: user.id })),
        getRequestImageConfig()
      );

      const imageBlob = new Blob([response.data], { type: "image/*" });
      const imageObjectURL = URL.createObjectURL(imageBlob);
      setImageData(imageObjectURL);
    } catch (error) {}
  };

  useEffect(() => {
    handleGetImage();
  }, []);

  const handleLoadImage = async (event) => {
    event.preventDefault();
    console.log("start");
    const formData = new FormData();
    formData.append("image", imageData);

    try {
      const response = await axios.post(
        api.profile.url,
        formData,
        getRequestImageConfig()
      );
      const imageBlob = new Blob([response.data], { type: "image/*" });
      const imageObjectURL = URL.createObjectURL(imageBlob);

      setImageData(imageObjectURL);
    } catch (imageData) {}
  };

  const handleFileChange = (event) => {
    setInput(true);
    const file = event.target.files[0];
    if (file) {
      setImageData(file);
    }
  };

  return (
    <>
      <Link>
        <Box
          sx={{
            display: "flex",
            flexDirection: "row",
            gap: "10px",
            alignItems: "center",
          }}
        >
          {imageData ? (
            <img
              src={imageData}
              alt="Аватар"
              style={{
                width: size + "px",
                height: size + "px",
                objectFit: "cover",
                borderRadius: "100%",
              }}
            />
          ) : (
            <Skeleton variant="circular" width={size} height={size} />
          )}

          <Typography variant="h6">{user.login}</Typography>
        </Box>
      </Link>

      {edit ? (
        <Box sx={{ marginLeft: "20px" }}>
          <form onSubmit={handleLoadImage}>
            <input
              type="file"
              accept="image/jpeg, image/png"
              onChange={handleFileChange}
            />

            {input ? (
              <Button size="small" type="submit">
                Изменить аватар
              </Button>
            ) : null}
          </form>
        </Box>
      ) : null}
    </>
  );
}

export default Author;
