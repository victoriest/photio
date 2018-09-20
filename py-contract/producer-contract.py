from flask import Flask, Response, make_response
import json
import config

app = Flask(__name__)


@app.route("/health")
def health():
    result = {'status': 'UP'}
    return Response(json.dumps(result), mimetype='application/json')


@app.route("/v1/api/invitation/invite")
def invite(token, scheduleId, targetUserId):
    pass


@app.route("/v1/api/invitation/accept")
def accept(token, invitationId):
    pass


@app.route("/v1/api/invitation/reject")
def reject(token, invitationId):
    pass


@app.route("/v1/api/invitation/evaluate")
def evaluate(token, targetUserId, socre, message):
    pass


if __name__ == "__main__":
    app.run(host=config.HOST, port=config.PORT, debug=config.DEBUG)
