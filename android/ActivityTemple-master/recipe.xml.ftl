<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
<#if !onlyFragment>
 <#include "../common/recipe_manifest.xml.ftl" />
</#if>

<#include "recipe_presenter.xml.ftl"/>
<#include "recipe_contact.xml.ftl"/>

<#if useFragment>
    <#include "recipe_fragment.xml.ftl" />
</#if>

<#if onlyFragment>
    <#include "recipe_fragment.xml.ftl" />
</#if>
    <#if !onlyFragment>
    <instantiate from="root/src/app_package/SimpleActivity.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}.kt" />
    <open file="${escapeXmlAttribute(srcOut)}/${activityClass}.kt}" />
    </#if>

<#if useFragment>
    <open file="${escapeXmlAttribute(resOut)}/layout/${fragmentLayoutName}.xml" />
<#else>
    <instantiate from="../common/root/res/layout/simple.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${layoutName}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${simpleLayoutName}.xml" />
</#if>
</recipe>
