package org.example

import groovy.json.JsonOutput
import spock.lang.Specification

class SpecificationWithEvidence extends Specification {

    void recordText(String description, String text) {
        reportInfo collapsibleDiv(description, "<pre>$text</pre>")
    }

    void recordJson(String description, String json) {
        reportInfo collapsibleDiv(description, "<pre>"+ JsonOutput.prettyPrint(json)+"</pre>")
    }

    static String collapsibleDiv(String description, String content) {
        """<details>
<summary>$description</summary>
<p>
$content
</p>
"""
    }

}
