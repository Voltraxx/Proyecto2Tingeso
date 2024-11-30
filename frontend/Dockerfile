# Usa la imagen base de Nginx
FROM nginx:alpine

# Copia los archivos de la carpeta dist (local) al directorio de Nginx en el contenedor
COPY dist /usr/share/nginx/html
COPY /nginx/conf.d /etc/nginx/conf.d

# Expone el puerto 80 para el tráfico HTTP
EXPOSE 3000

# Comando de inicio de Nginx
CMD ["nginx", "-g", "daemon off;"]
