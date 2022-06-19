package com.hollingsworth.arsnouveau.common.datagen.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.common.block.ArchwoodChest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import static com.hollingsworth.arsnouveau.api.RegistryHelper.getRegistryName;

public class PatchouliBuilder {

    JsonObject object = new JsonObject();
    JsonArray pages = new JsonArray();
    int textCounter;
    String name;
    public ResourceLocation category;

    public PatchouliBuilder(ResourceLocation category, String name) {
        this.category = category;
        this.withName(name.contains(".") ? name : "ars_nouveau.page." + name);
        this.name = name;
        this.withCategory(category);
    }

    public PatchouliBuilder(ResourceLocation category, ItemLike itemLike){
        this.category = category;
        withName(itemLike.asItem().getDescriptionId());
        this.name = getRegistryName(itemLike.asItem()).getPath();
        withIcon(itemLike);
        this.withCategory(category);
    }

    public PatchouliBuilder(ResourceLocation category, RegistryObject<? extends ItemLike> itemLike) {
        this(category, itemLike.get());
    }

    public PatchouliBuilder withName(String path){
        object.addProperty("name", path);
        this.name = path;
        return this;
    }

    public PatchouliBuilder withSortNum(int num){
        object.addProperty("sortnum", num);
        return this;
    }

    public PatchouliBuilder withPage(IPatchouliPage page){
        pages.add(page.build());
        return this;
    }

    public PatchouliBuilder withIcon(String path){
        object.addProperty("icon", path);
        return this;
    }

    public PatchouliBuilder withIcon(ItemLike item){
        object.addProperty("icon", getRegistryName(item.asItem()).toString());
        return this;
    }

    public PatchouliBuilder withIcon(RegistryObject item){
        object.addProperty("icon", getRegistryName(((ItemLike)item.get()).asItem()).toString());
        return this;
    }

    private PatchouliBuilder withCategory(ResourceLocation path){
        object.addProperty("category", path.toString());
        return this;
    }

    public PatchouliBuilder withTextPage(String contents){
        pages.add(new TextPage(contents).build());
        return this;
    }

    public PatchouliBuilder withLocalizedText(String id){
        textCounter++;
        return withTextPage("ars_nouveau.page" + textCounter + "." + id);
    }

    public PatchouliBuilder withLocalizedText(){
        return withLocalizedText(this.name);
    }

    public JsonObject build(){
        this.object.add("pages", pages);
        return this.object;
    }
}
