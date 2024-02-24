FROM elasticsearch:7.10.1

COPY ./elasticsearch.yml /usr/share/elasticsearch/config/elasticsearch.yml
# Set environment variables for Elasticsearch configuration
ENV discovery.type=single-node

ENV bootstrap.memory_lock=true
ENV cluster.routing.allocation.disk.threshold_enabled=false

# Expose the Elasticsearch ports
EXPOSE 9200 9300

# Start Elasticsearch when the container launches
CMD ["elasticsearch"]