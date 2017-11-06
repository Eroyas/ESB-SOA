FROM petitroll/esb

# Build: docker build -t esb .
# Run:   docker run -v ./_in:/servicemix/camel/input -v ./_out:/servicemix/camel/output esb
# Overriding: docker run -it --entrypoint "sh" esb

RUN mkdir -p camel/input camel/output

# Deploying technical flows
COPY ./target/flows-1.0.jar ./deploy/.

EXPOSE 8181 8101 1099 61616 44444