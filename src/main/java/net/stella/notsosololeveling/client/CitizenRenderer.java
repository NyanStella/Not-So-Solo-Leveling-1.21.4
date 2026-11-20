package net.stella.notsosololeveling.client;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.resources.ResourceLocation;
import net.stella.notsosololeveling.citizen.CitizenEntity;

public class CitizenRenderer
    extends MobRenderer<CitizenEntity, VillagerRenderState, VillagerModel>{

        private static final ResourceLocation TEXTURE =
                ResourceLocation.withDefaultNamespace("textures/entity/villager/villager.png");

        public CitizenRenderer(EntityRendererProvider.Context context){
            super(context, new VillagerModel(context.bakeLayer(ModelLayers.VILLAGER)), 0.5F);
        }

        @Override
                public VillagerRenderState createRenderState(){
            return new VillagerRenderState();
        }

        @Override
                public ResourceLocation getTextureLocation(VillagerRenderState state){
            return TEXTURE;
        }
    }
