FROM kibana:7.10.1

COPY ./ /usr/share/kibana/config/
# ENV ELASTICSEARCH_URL=http://elasticsearch:9200

# Expose the Kibana port
EXPOSE 5601

# Start Kibana when the container launches
CMD ["kibana"]