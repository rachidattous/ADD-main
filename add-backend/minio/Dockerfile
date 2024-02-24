FROM minio/minio

RUN mkdir -p /data

# Expose the MinIO ports
EXPOSE 9000

# Start MinIO when the container launches
# CMD ["minio", "server", "/data"]
CMD ["minio", "server",  "--address", ":9000", "--console-address", ":9001" , "/data"]