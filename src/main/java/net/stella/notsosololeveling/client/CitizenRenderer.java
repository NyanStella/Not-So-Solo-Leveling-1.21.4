package net.stella.notsosololeveling.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.resources.ResourceLocation;
import net.stella.notsosololeveling.citizen.CitizenEntity;

public class CitizenRenderer
    extends MobRenderer<CitizenEntity, VillagerRenderState, VillagerModel>{

        private static final ResourceLocation TEXTURE =
                ResourceLocation.withDefaultNamespace("textures/entity/villager/villager.png");

        private final VillagerModel adultModel;
        private final VillagerModel babyModel;

        public CitizenRenderer(EntityRendererProvider.Context context){
            super(context, new VillagerModel(context.bakeLayer(ModelLayers.VILLAGER)), 0.5F);
            adultModel = getModel();
            babyModel = new VillagerModel(context.bakeLayer(ModelLayers.VILLAGER_BABY));
        }

        @Override
        public void render(
                VillagerRenderState state,
                PoseStack poseStack,
                MultiBufferSource buffer,
                int packedLight
        ){
            model = state.isBaby ? babyModel : adultModel;
            super.render(state, poseStack, buffer, packedLight);
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
