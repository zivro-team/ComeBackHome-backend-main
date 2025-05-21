#!/usr/bin/env bash
#   Use this script to test if a given TCP host/port are available

WAITFORIT_cmdname=${0##*/}

echoerr() { if [[ $WAITFORIT_QUIET -ne 1 ]]; then echo "$@" 1>&2; fi }

wait_for() {
  if [[ "$WAITFORIT_TIMEOUT" -gt 0 ]]; then
    echoerr "$WAITFORIT_cmdname: waiting $WAITFORIT_TIMEOUT seconds for $WAITFORIT_HOST:$WAITFORIT_PORT"
  else
    echoerr "$WAITFORIT_cmdname: waiting for $WAITFORIT_HOST:$WAITFORIT_PORT without a timeout"
  fi
  WAITFORIT_start_ts=$(date +%s)
  while :
  do
    nc -z "$WAITFORIT_HOST" "$WAITFORIT_PORT" > /dev/null 2>&1
    result=$?
    if [[ $result -eq 0 ]]; then
      WAITFORIT_end_ts=$(date +%s)
      echoerr "$WAITFORIT_cmdname: $WAITFORIT_HOST:$WAITFORIT_PORT is available after $((WAITFORIT_end_ts - WAITFORIT_start_ts)) seconds"
      break
    fi
    sleep 1
  done
  return $result
}

wait_for_wrapper() {
  # In order to support SIGINT during timeout: http://unix.stackexchange.com/a/57692
  if [[ $WAITFORIT_TIMEOUT -gt 0 ]]; then
    timeout "$WAITFORIT_TIMEOUT" bash -c wait_for
    result=$?
    if [[ $result -eq 124 ]]; then
      echoerr "$WAITFORIT_cmdname: timeout occurred after waiting $WAITFORIT_TIMEOUT seconds for $WAITFORIT_HOST:$WAITFORIT_PORT"
    fi
    return $result
  else
    wait_for
    return $?
  fi
}

WAITFORIT_TIMEOUT=15
WAITFORIT_QUIET=0

while [[ $# -gt 0 ]]
do
  case "$1" in
    *:* )
    WAITFORIT_HOST="${1%:*}"
    WAITFORIT_PORT="${1#*:}"
    shift 1
    ;;
    --timeout=*)
    WAITFORIT_TIMEOUT="${1#*=}"
    shift 1
    ;;
    --quiet)
    WAITFORIT_QUIET=1
    shift 1
    ;;
    --)
    shift
    break
    ;;
    *)
    echoerr "Unknown argument: $1"
    exit 1
    ;;
  esac
done

if [[ "$WAITFORIT_HOST" == "" || "$WAITFORIT_PORT" == "" ]]; then
  echo "Usage: $WAITFORIT_cmdname host:port [--timeout=seconds] [--quiet] [-- command args]"
  exit 1
fi

wait_for_wrapper
RESULT=$?

if [[ $RESULT -ne 0 ]]; then
  exit $RESULT
fi

if [[ $# -gt 0 ]]; then
  exec "$@"
else
  exit 0
fi
