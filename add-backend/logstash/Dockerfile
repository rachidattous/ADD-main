# Use the official Logstash image as the base image
FROM logstash:7.10.1

# Copy Logstash configuration file
COPY ./config/ /etc/logstash/conf.d/

# Expose the Logstash ports
EXPOSE 9999

# Start Logstash with the custom configuration file
CMD ["-f", "/etc/logstash/conf.d/"]