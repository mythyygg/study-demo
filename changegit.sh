#!/bin/bash

while [[ $# -gt 1 ]]
do
key="$1"

case $key in
	-c| --commits)
	COMMITS="$2"
	shift
	;;
	*)
	# unknown option
	;;
esac
shift
done

die () {
    echo >&2 `basename $0`: $*
    exit 1
}

tmpfile=$(mktemp gitblah-XXXX)
[ -f "$tmpfile" ] || die "could not get tmpfile=[$tmpfile]"
trap "rm -f $tmpfile" EXIT

if [ -n "${COMMITS+set}" ];
then git log -n $COMMITS --pretty=format:"%cI | %H | %s" > $tmpfile;
else git log -n 5 --pretty=format:"%cI | %H | %s" > $tmpfile;
fi

${VISUAL:-${EDITOR:-vi}} $tmpfile


ENVFILTER=""
while read commit; do
	IFS="|" read date hash message <<< "$commit"
	DATE_NO_SPACE="$(echo "${date}" | tr -d '[[:space:]]')"
	COMMIT_ENV=$(cat <<-END
	     if [ \$GIT_COMMIT = $hash ];
	     then
		 export GIT_AUTHOR_DATE="$DATE_NO_SPACE"
		 export GIT_COMMITTER_DATE="$DATE_NO_SPACE";
	     fi;
	END
	)
	ENVFILTER="$ENVFILTER$COMMIT_ENV"
done < $tmpfile

git filter-branch -f --env-filter "$ENVFILTER" >/dev/null
if [ $? = 0 ] ; then
    echo "Git commit dates updated. Run 'git push -f BRANCH_NAME' to push your changes."
else
    echo "Git redate failed. Please make sure you run this on a clean working directory."
    fi
